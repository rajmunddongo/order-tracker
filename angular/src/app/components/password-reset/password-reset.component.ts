import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.scss']
})
export class PasswordResetComponent implements OnInit {


  constructor(private route: ActivatedRoute,private userService:UserService) { }
  pass1: string = '';
  pass2: string = '';
  link:string = '';
  ngOnInit(): void {
    const link = this.route.snapshot.queryParamMap.get('pass');
    if(link!=null) this.link=link;
    console.log("link:", link);
  }

  resetPass() {
    if(this.pass1==this.pass2) {
      this.userService.resetPassword(this.pass1,this.link).subscribe();
    }
  }
}
