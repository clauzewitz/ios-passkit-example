db = db.getSiblingDB('demo');
db.templates.insertMany([
    {
        storeId: "397728319",
        name: "E Mart",
        desc: "이마트",
        thumbnail: "https://material-ui.com/static/images/cards/contemplative-reptile.jpg",
        logoPath: "/static/emart/logo.png",
        logoRetinaPath: "/static/emart/logo@2x.png",
        content: "test content",
        tags: ["mart"],
        createDate: Date.now()
    },
    {
        storeId: "429151787",
        name: "Homeplus",
        desc: "홈플러스",
        thumbnail: "https://material-ui.com/static/images/cards/contemplative-reptile.jpg",
        logoPath: "/static/homeplus/logo.png",
        logoRetinaPath: "/static/homeplus/logo@2x.png",
        content: "test content",
        tags: ["mart"],
        createDate: Date.now()
    },
    {
        storeId: "473250588",
        name: "L Point",
        desc: "엘포인트",
        thumbnail: "https://material-ui.com/static/images/cards/contemplative-reptile.jpg",
        logoPath: "/static/lpoint/logo.png",
        logoRetinaPath: "/static/lpoint/logo@2x.png",
        content: "test content",
        tags: ["mart"],
        createDate: Date.now()
    },
    {
        storeId: "376313183",
        name: "Happy Point",
        desc: "해피포인트",
        thumbnail: "https://material-ui.com/static/images/cards/contemplative-reptile.jpg",
        logoPath: "/static/happypoint/logo.png",
        logoRetinaPath: "/static/happypoint/logo@2x.png",
        content: "test content",
        tags: [],
        createDate: Date.now()
    },
    {
        storeId: "358731598",
        name: "OK Cashbag",
        desc: "OK 캐쉬백",
        thumbnail: "https://material-ui.com/static/images/cards/contemplative-reptile.jpg",
        logoPath: "/static/okcashbag/logo.png",
        logoRetinaPath: "/static/okcashbag/logo@2x.png",
        content: "test content",
        tags: [],
        createDate: Date.now()
    },
    {
        storeId: "464205249",
        name: "T Membership",
        desc: "T 멤버십",
        thumbnail: "https://material-ui.com/static/images/cards/contemplative-reptile.jpg",
        logoPath: "/static/skt/logo.png",
        logoRetinaPath: "/static/skt/logo@2x.png",
        content: "test content",
        tags: ["telecom"],
        createDate: Date.now()
    },
    {
        storeId: "451095024",
        name: "KT Membership",
        desc: "KT 멤버십",
        thumbnail: "https://material-ui.com/static/images/cards/contemplative-reptile.jpg",
        logoPath: "/static/kt/logo.png",
        logoRetinaPath: "/static/kt/logo@2x.png",
        content: "test content",
        tags: ["telecom"],
        createDate: Date.now()
    },
    {
        storeId: "958811075",
        name: "U+ Membership",
        desc: "U+ 멤버십",
        thumbnail: "https://material-ui.com/static/images/cards/contemplative-reptile.jpg",
        logoPath: "/static/uplus/logo.png",
        logoRetinaPath: "/static/uplus/logo@2x.png",
        content: "test content",
        tags: ["telecom"],
        createDate: Date.now()
    }
])