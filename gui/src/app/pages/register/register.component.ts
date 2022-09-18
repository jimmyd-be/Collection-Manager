import { Component, OnInit } from '@angular/core';
import {UntypedFormControl, UntypedFormGroup} from "@angular/forms";
import {AuthService} from "../../Services/auth.service";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerForm = new UntypedFormGroup({
    fullName: new UntypedFormControl(''),
    email: new UntypedFormControl(''),
    password: new UntypedFormControl(''),
    confirmPassword: new UntypedFormControl(''),
  });

  constructor(private authService: AuthService,
              private router: Router,
              private messageService: MessageService) { }

  onSubmit() {
    this.authService.register(this.registerForm.controls['fullName'].value,
      this.registerForm.controls['email'].value,
    this.registerForm.controls['password'].value,
    this.registerForm.controls['confirmPassword'].value).subscribe(success =>
      this.router.navigate(['/auth/login'])
      , error => this.messageService.add({severity:'error', summary:'', detail:'Something went wrong'})
    );

  }
}
