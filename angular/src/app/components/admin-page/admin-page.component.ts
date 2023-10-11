import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.type';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.scss']
})
export class AdminPageComponent implements OnInit {
  constructor(private userService:UserService,private authService:AuthService,private router:Router) {}
  users: User[] = []; 


  ngOnInit(): void {
    this.authService.isAdmin().subscribe(result => {
      if(!result) {
        this.router.navigate(['/']);
      }
    });
    this.userService.getUsers()
      .subscribe(data => {
        this.users=data;
  });
  }

  deleteUser(id:number) : void {
    this.userService.deleteUser(id).subscribe();
    this.userService.getUsers()
    .subscribe(data => {
      this.users=data;
    });
  }
}
