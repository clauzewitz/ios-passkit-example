import { isUndefined, isEmpty, isNumber, isString } from 'lodash';
import dayjs from 'dayjs';

const CHECK_ZIPCODE_MIN_LENGTH: number = 2;
const CHECK_ZIPCODE_MAX_LENGTH: number = 5;
const REGEX_EMAIL: RegExp = new RegExp('^(?<localPart>([^<>()\\[\\]\\\\.,;:\\s@"]+(.[^<>()\\[\\]\\\\.,;:\\s@"]+)*)|(".+"))@(?<domainPart>(\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$', 'g');
const REGEX_NUMBER_WITH_COMMA: RegExp = new RegExp('\\B(?=(\\d{3})+(?!\\d))', 'g');
const REGEX_NUMBER_WITH_SPACE: RegExp = new RegExp('.{1,4}', 'g');

export default class CommonUtil {
    
    static generateKey = (pre: string): string => {
        return `${pre}_${new Date().getTime()}`;
    };

    static getUnixTimestamp = (): number => {
        return dayjs().unix();
    }

    static getUnixTimestampByMilliseconds = (): number => {
        return dayjs().valueOf();
    }

    static checkKeyword = (keyword: string): boolean => {
        return (!isUndefined(keyword) && !isEmpty(keyword.replace(/^\s+|\s+$/g, '')));
    };

    static isNumber = (value: any): boolean => {
        let isValid = false;
        
        if (typeof value === 'number') {
            isValid = true;
        } else if (typeof value === 'string') {
            isValid = /^\d{1,}$/.test(value);
        }

        return isValid;
    };

    static checkKeywordOfZipCode = (searchKeyword: string, length?: number): boolean => {
        let isValid = CommonUtil.isNumber(searchKeyword);

        if (!isValid) {
            return isValid;
        }

        let checkZipCodeLength = `${CHECK_ZIPCODE_MIN_LENGTH},${CHECK_ZIPCODE_MAX_LENGTH}`;

        if (!!length && length < CHECK_ZIPCODE_MAX_LENGTH) {
            checkZipCodeLength = `${length},${CHECK_ZIPCODE_MAX_LENGTH}`;
        } else if (!length || length >= CHECK_ZIPCODE_MAX_LENGTH) {
            checkZipCodeLength = `${CHECK_ZIPCODE_MAX_LENGTH}`;
        }

        const regex = new RegExp(`^\\d{${checkZipCodeLength}}$`);

        if (isValid) {
            isValid = regex.test(searchKeyword);;
        }

        return isValid;
    };

    static getLocalPartFromEmail = (email: string): string | undefined => {

        if (isUndefined(email)) {
            return '';
        }

        if (REGEX_EMAIL.test(email)) {
            return REGEX_EMAIL.exec(email)?.groups?.localPart;
        } else {
            return email;
        }
    };

    static getDomainPartFromEmail = (email: string): string | undefined => {

        if (isUndefined(email)) {
            return '';
        }

        if (REGEX_EMAIL.test(email)) {
            return REGEX_EMAIL.exec(email)?.groups?.domainPart;
        } else {
            return email;
        }
    };

    static getPresentLocationPath = (): string | undefined => {

        if (isEmpty(window.location.hash)) {
            return window.location.pathname;
        }

        return window.location.hash.replace('#', '').split('?').shift();
    };

    static getQueryParamMap = (): URLSearchParams => {
        const pathname: string = window.location.hash || window.location.search;

        return new URLSearchParams(pathname.split('?').pop());
    };

    static toNumberWithComma = (value: number | string | undefined): string | undefined => {

        if (isUndefined(value)) {
            return value;
        }

        if (isNumber(value)) {
            return value.toString().replace(REGEX_NUMBER_WITH_COMMA, ' ');
        } else if (isString(value)) {
            return value.replace(REGEX_NUMBER_WITH_COMMA, ' ');
        }

        return value;
    };

    static toNumberWithSpace = (value: number | string | undefined): string | undefined => {

        if (isUndefined(value)) {
            return value;
        }

        if (isNumber(value)) {
            return value.toString().match(REGEX_NUMBER_WITH_SPACE)?.join(' ');
        } else if (isString(value)) {
            return value.match(REGEX_NUMBER_WITH_SPACE)?.join(' ');
        }

        return value;
    };
}