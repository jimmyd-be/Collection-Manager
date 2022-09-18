import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../Services/auth.service";
import {TokenService} from "../../Services/token.service";
import {UntypedFormControl, UntypedFormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import {Message, MessageService} from "primeng/api";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm = new UntypedFormGroup({
    email: new UntypedFormControl(''),
    password: new UntypedFormControl(''),
  });

  constructor(private authService: AuthService,
              private tokenService: TokenService,
              private router: Router,
              private messageService: MessageService) {
  }

  onSubmit() {
    this.authService.login(this.loginForm.controls['email'].value, this.loginForm.controls['password'].value)
      .subscribe(token => {
      this.tokenService.saveToken(token.token);
      this.router.navigate(['/pages/dashboard']);
    },
      error => this.messageService.add({severity:'error', summary:'', detail:'Credentials are not valid'})
      );
  }

}
