import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';
import timezone from 'dayjs/plugin/timezone';
dayjs.extend(timezone);

export const getFormattedDate = (date: string | null): string => {
  if (!date) {
    return dayjs().format('YYYY-MM-DD HH:mm:ss');
  }
  return dayjs(date).format('YYYY-MM-DD HH:mm:ss');
};
