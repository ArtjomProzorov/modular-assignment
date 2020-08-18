import {notification} from 'antd';

export const errorWithMessage = (message) => notification.error({
    message: 'Error',
    description: message,
    placement: 'bottomRight',
});

export const success = (message) => notification.success({
    message: 'Success',
    description: message,
    placement: 'bottomRight',
});