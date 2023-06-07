export const truncateText = (text: string, maxLength: number) => {
  const allowedTags = ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'img', '```']; // 허용된 태그 리스트
  const tagsRegExp = new RegExp(`</?(${allowedTags.join('|')})[^>]*>`, 'gi'); // 허용된 태그에 대한 정규식
  const plainText = text
    .replace(tagsRegExp, '')
    .replace(/<\/?br\s?\/?>/gi, '\n'); // 허용된 태그와 <br> 태그를 제거하고 줄바꿈 문자(\n)으로 대체
  if (plainText.length > maxLength) {
    return plainText.slice(0, maxLength) + '...';
  }
  return plainText;
};
