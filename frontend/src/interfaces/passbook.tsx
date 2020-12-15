import { BARCODE_TYPE, QR_TYPE } from 'enums';

interface iPassbook {
    id: string | undefined,
    name: string,
    membershipNum: string,
    displayMembershipNum: string | undefined,
    barcodeType: BARCODE_TYPE,
    qrType: QR_TYPE
};

export default iPassbook;