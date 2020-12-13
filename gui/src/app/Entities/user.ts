export class User {

    constructor(
        public id: number,
        public username: string,
        public mail: string,
        public isAdmin: boolean,
        public creationDate: Date,
        public theme: string) {}
}
