import { UserProfile } from '../../types';
import styled from '@emotion/styled';

const CheckedUsers = ({
  checkedUsers,
  onClickDelete,
}: {
  checkedUsers: UserProfile[];
  onClickDelete: (user: UserProfile) => void;
}) => {
  return (
    <S.Container>
      {checkedUsers?.map(user => (
        <S.User
          onClick={() => {
            onClickDelete(user);
          }}
        >
          <S.UserImage src={user.imageUrl} />
          <S.UserName>{user.name}</S.UserName>
        </S.User>
      ))}
    </S.Container>
  );
};

const S = {
  Container: styled.div`
    display: flex;
    gap: 10px;
    overflow: auto hidden;

    ::-webkit-scrollbar {
      width: 1px;
      overflow-x: hidden;
      background-color: transparent;
    }
  `,
  UserImage: styled.img`
    width: 2.5rem;
    height: 2.5rem;
    border-radius: 50%;
    object-fit: cover;
  `,
  User: styled.div`
    display: flex;
    flex-direction: column;
    gap: 5px;
    align-items: center;
    cursor: pointer;
  `,
  UserName: styled.span`
    font-size: 15px;
  `,
};
export default CheckedUsers;
