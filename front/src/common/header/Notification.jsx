import React, { useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import {
  roomInfoState,
  loadingState,
  scheduleInfo,
  userMarkers,
  chatMessages,
} from '../../app/store';
import classes from './Notification.module.scss';
import Loading from '../loading/Loading';

const colorCode = [
  '#EB5252',
  '#7997FE',
  '#90CE0A',
  '#61D9C3',
  '#8059D1',
  '#FF7BBA',
];

function Notification({ notificaiton, userInfo }) {
  const [loading, setLoading] = useRecoilState(loadingState);
  const [scheduleInfoState, setScheduleInfoState] =
    useRecoilState(scheduleInfo);
  const [forLoadMarker, setForLoadMarker] = useRecoilState(userMarkers);
  const [chatMessa, setChatMessa] = useRecoilState(chatMessages);
  const [roomInfo, setRoomInfo] = useRecoilState(roomInfoState);
  const [moveFlag, setMoveFlag] = useState(false);
  const navigate = useNavigate();
  const instance = axios.create({
    baseURL: `https://i8b202.p.ssafy.io/api`,
    headers: {
      Authorization: `Bearer ${userInfo.token}`,
      contentType: 'application/json',
    },
  });

  const moveNavi = () => {
    return navigate('/room/search');
  };

  const onConfirm = async () => {
    console.log(notificaiton.readOrNot);
    if (!notificaiton.readOrNot) {
      try {
        const resNotification = await instance.patch('/notification', {
          notificationId: notificaiton.notificationId,
        });
        const resRoomInfo = await instance.get(`/rooms/${notificaiton.roomId}`);
        const resRoomMemList = await instance.get(
          `/rooms/users/${notificaiton.roomId}`
        );
        console.log(notificaiton.roomId);
        const colorCoded = colorCode[resRoomMemList.data.length];
        const resRoomRegist = await instance.post('/rooms/users', {
          roomId: notificaiton.roomId,
          colorCode: colorCoded,
        });
        console.log(resRoomRegist.data);
        setRoomInfo({
          roomId: resRoomInfo.data.roomId,
          roomName: resRoomInfo.data.roomName,
          startDate: resRoomInfo.data.startDate,
          endDate: resRoomInfo.data.endDate,
          colorCode: colorCoded,
        });
        // ????????? ?????? ????????????
        const storageInfo = await instance.get(
          `/storages/rooms/${notificaiton.roomId}`
        );
        console.log('????????? ????????????');
        console.log(storageInfo.data);
        if (storageInfo.data.length > 0) {
          const storageMarkerData = storageInfo.data.map(marker =>
            // const markerColor = await instance.get('')
            setForLoadMarker(forLoadMarker => [
              ...forLoadMarker,
              {
                id: marker.id,
                x: marker.lng,
                y: marker.lat,
                isconfirmed: marker.confirmed,
                categoryCode: marker.categoryName,
                title: marker.storageName,
                colorCode: marker.colorCode,
              },
            ])
          );
          // // ????????? ?????? store ??????
          const scheduleData = {};
          const storageSchedule = storageInfo.data.filter(
            data => data.confirmed !== false
          );
          if (storageSchedule.length > 0) {
            // max ??? ?????????
            let maxDayOrder = 0;
            for (let i = 0; i < storageSchedule.length; i += 1) {
              if (maxDayOrder < storageSchedule[i].dayOrder) {
                maxDayOrder = storageSchedule[i].dayOrder;
              }
            }
            if (maxDayOrder === 0) {
              const scheduleAllData = {
                date: resRoomInfo.data.startDate,
                items: [],
              };
              for (let i = 0; i < storageSchedule.length; i += 1) {
                if (storageSchedule[i].indexOrder === i) {
                  scheduleAllData.items.push(storageSchedule[i]);
                }
              }
            } else {
              const scheduleAllData = {};
              const AddDays = (date, day) => {
                const result = new Date(date);
                result.setDate(result.getDate() + day);
                return result;
              };
              for (let j = 0; j < maxDayOrder; j += 1) {
                const filteredSc = storageSchedule.filter(
                  storageSchedule.dayOrder === j
                );
                const forSetDate = AddDays(resRoomInfo.data.startDate, j);
                const scheduleIndiData = {
                  data: forSetDate,
                  items: [],
                };
                for (let k = 0; k < filteredSc.length; k += 1) {
                  if (k === filteredSc[k].indexOrder) {
                    scheduleIndiData.items.push(filteredSc[k]);
                  }
                }
                scheduleAllData.push(scheduleIndiData);
              }
              setScheduleInfoState(scheduleAllData);
            }
          }
        }
        // ?????? ????????????
        const chatInfo = await instance.get(`/chatting/${notificaiton.roomId}`);
        setChatMessa(chatInfo.data);
        console.log(chatInfo.data);
        // // ?????? ?????????
        // const voteInfo = await instance.get(`/votes/${roomID}`);
        // console.log(voteInfo.data);
        // if (voteInfo.data.length > 0) {
        // // }
        setLoading(true);
        setTimeout(() => {
          setLoading(false);
          moveNavi();
        }, 2000);
      } catch (error) {
        console.log(error);
      }
    }
  };

  return (
    <div className={classes.notificaiton_container}>
      <div className={classes.notificaiton_header}>
        <i className='bx bxs-bell-ring'></i>
        <p>{notificaiton.createdAt}</p>
      </div>
      <div className={classes.notificaiton_p}>
        {/* ??????????????? ??? ???????????? ????????? */}
        <p>
          {notificaiton.sendMemberName}?????? &apos;{notificaiton.roomName}&apos;
          ?????? ???????????????
        </p>
      </div>
      <div className={classes.btnList}>
        <button
          className={`${classes.btn} ${classes.confirmBtn}`}
          onClick={onConfirm}
        >
          ??????
        </button>
        <button className={`${classes.btn} ${classes.rejectBtn}`}>??????</button>
      </div>
      <div className={classes.notificaiton_line}></div>
    </div>
  );
}

export default Notification;
