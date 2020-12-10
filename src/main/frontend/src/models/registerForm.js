export default class RegisterForm {
    constructor(email, pseudo, firstName, lastName, password, passwordAgain, city) {
        this.email = email;
        this.pseudo = pseudo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.passwordAgain = passwordAgain;
        this.city = city;
    }
}