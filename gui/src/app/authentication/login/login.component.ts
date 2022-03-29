import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../Services/auth.service";
import {TokenService} from "../../Services/token.service";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  email: string;
  password: string;

  profileForm = new FormGroup({
    email: new FormControl(''),
    password: new FormControl(''),
  });


  constructor(private authService: AuthService,
              private tokenService: TokenService) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.authService.login(this.email, this.password).subscribe(token =>
      this.tokenService.saveToken(token.token)
    );
  }

}
