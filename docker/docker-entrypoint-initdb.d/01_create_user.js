db = db.getSiblingDB('demo');
db.createUser(
    {
        user: 'demoUser',
        pwd: 'demoUser',
        roles: [{ role: 'readWrite', db: 'demo' }]
    }
);