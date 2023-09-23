import { Component } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-forgotpassword',
  templateUrl: './forgotpassword.component.html',
  styleUrls: ['./forgotpassword.component.scss'],
  providers: [UserService]
})
export class ForgotpasswordComponent {
  email: string = '';

  constructor(private  userservice: UserService) {}

  sendResetEmail(email: string) {
    console.log('Email to send reset:', email);
    
    this.userservice.resetEmail(email).subscribe();
  }
}
