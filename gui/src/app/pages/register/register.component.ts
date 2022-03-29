import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {AuthService} from "../../Services/auth.service";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm = new FormGroup({
    fullName: new FormControl(''),
    email: new FormControl(''),
    password: new FormControl(''),
    confirmPassword: new FormControl(''),
  });

  constructor(private authService: AuthService,
              private router: Router,
              private messageService: MessageService) { }

  ngOnInit(): void {
  }

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
