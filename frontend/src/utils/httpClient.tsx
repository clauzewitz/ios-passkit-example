import { axiosClient } from 'configs';
import { getReasonPhrase } from 'http-status-codes'

export default class HttpHelper {

    static async get(url: string, config?: any): Promise<any> {
        try {
            return await axiosClient.get(url, config);
        } catch(error) {
            throw new Error(getReasonPhrase(error.status));
        }
    }

    static async post(url: string, param: any, config?: any): Promise<any> {
        try {
            return await axiosClient.post(url, param, config);
        } catch(error: any) {
            throw new Error(getReasonPhrase(error.status));
        }
    }

    static async put(url: string, param: any, config?: any): Promise<any> {
        try {
            return await axiosClient.put(url, param, config);
        } catch(error) {
            throw new Error(getReasonPhrase(error.status));
        }
    }

    static async delete(url: string, config?: any): Promise<any> {
        try {
            return await axiosClient.delete(url, config);
        } catch(error) {
            throw new Error(getReasonPhrase(error.status));
        }
    }
};