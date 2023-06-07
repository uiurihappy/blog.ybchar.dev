import dayjs from 'dayjs';
import timezone from 'dayjs/plugin/timezone';
dayjs.extend(timezone);

export const getFormattedDate = (date: string | null): string => {
  if (!date) {
    return dayjs().format('YYYY년 MM월 DD일 A H:mm');
  }
  return dayjs(date).format('YYYY년 MM월 DD일 A H:mm');
};
