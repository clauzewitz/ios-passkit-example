import axios from 'axios';
import { isNil } from 'lodash';
import { StatusCodes } from 'http-status-codes'

const axiosClient = axios.create({
    baseURL: process.env.REACT_APP_API_ENDPOINT,
});

axiosClient.interceptors.request.use((config) => {
    return config;
}, (error) => {
    return Promise.reject(error);
});

axiosClient.interceptors.response.use((response) => {
    return response;
}, (error) => {
    const errorResponse = error.response;

    if (isNil(errorResponse)) {
        alert(`서버와의 통신에 실패하였습니다.\n다음에 다시 시도해주십시오.`);
        return Promise.reject({
            data: undefined,
            status: StatusCodes.SERVICE_UNAVAILABLE
        });
    }

    switch (errorResponse.status) {
        case StatusCodes.UNAUTHORIZED:
        case StatusCodes.FORBIDDEN:
            alert(`인증되지 않은 접근입니다.`);
            break;
        case StatusCodes.NOT_FOUND:
            alert(`찾을 수 없습니다.`);
            break;
        case StatusCodes.BAD_REQUEST:
            alert(`잘못된 요청입니다.`);
            break;
        case StatusCodes.INTERNAL_SERVER_ERROR:
        case StatusCodes.SERVICE_UNAVAILABLE:
        case StatusCodes.METHOD_NOT_ALLOWED:
        default:
            alert(`서버와의 통신에 실패하였습니다.\n다음에 다시 시도해주십시오.`);
    }

    return Promise.reject({
        data: errorResponse.data,
        status: errorResponse.status
    });
});

export default axiosClient;