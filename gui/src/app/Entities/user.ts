export class User {

  constructor(
    public id: number,
    public username: string,
    public mail: string,
    public admin: boolean,
    public creationDate: Date,
    public theme: string) {
  }
}
