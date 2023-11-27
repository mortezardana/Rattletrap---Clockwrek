import { Component, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';

import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'app/config/error.constants';
import { RegisterService } from './register.service';
import Web3 from 'web3';

@Component({
  selector: 'jhi-register',
  templateUrl: './register.component.html',
})
export class RegisterComponent implements AfterViewInit {
  @ViewChild('login', { static: false })
  login?: ElementRef;

  web3: Web3;

  doNotMatch = false;
  error = false;
  errorEmailExists = false;
  errorUserExists = false;
  success = false;

  registerForm = new FormGroup({
    login: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    }),
    email: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email],
    }),
    password: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(4), Validators.maxLength(50)],
    }),
    confirmPassword: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(4), Validators.maxLength(50)],
    }),
  });

  constructor(private translateService: TranslateService, private registerService: RegisterService) {
    this.web3 = new Web3('HTTP://127.0.0.1:8545');
  }

  ngAfterViewInit(): void {
    if (this.login) {
      this.login.nativeElement.focus();
    }
  }

  register(): void {
    this.doNotMatch = false;
    this.error = false;
    this.errorEmailExists = false;
    this.errorUserExists = false;

    const { password, confirmPassword } = this.registerForm.getRawValue();
    if (password !== confirmPassword) {
      this.doNotMatch = true;
    } else {
      const { login, email } = this.registerForm.getRawValue();
      this.registerService
        .save({ login, email, password, langKey: this.translateService.currentLang })
        .subscribe({ next: () => (this.success = true), error: response => this.processError(response) });
    }
  }

  checkUserPublicAddy(event: any): void {
    let requestAccount = event.view.window.ethereum.request({ method: 'eth_requestAccounts' });
    let publicAddress: string;
    let nounce: string;

    requestAccount
      .then((result: any) => {
        publicAddress = result[0];
        this.registerService
          .checkPublicAdd(publicAddress)
          .subscribe({ next: () => (this.success = true), error: response => this.processError(response) });
        console.log('Request to check public address ______________________________________________________________');
      })
      .catch((error: any) => {
        console.log(
          "requesting to check if this user's wallet's public address is registered in the platform failed with response: ",
          error
        );
      })
      .finally(() => {
        console.log('Requested to check if public address provided is registered in the platform!');
      });
  }

  walletRegister(event: any): void {
    let requestAccount = event.view.window.ethereum.request({ method: 'eth_requestAccounts' });
    let requestSign = event.view.window.ethereum.request({
      method: 'eth_sign',
      params: ['0xb82dbad89bf7dea41ddeb2782b68f3acba6295f3', this.web3.utils.sha3('test')],
      message: 'this message is shown in metamask',
    });
    let publicAddress: string;
    let nounce: string;

    requestAccount
      .then((result: any) => {
        publicAddress = result[0];
        let hashNonce = requestSign;
        console.log('Request account was successful with this result: ', publicAddress);
        this.registerService
          .registerWallet(publicAddress, hashNonce)
          .subscribe({ next: () => (this.success = true), error: response => this.processError(response) });
      })
      .catch((error: any) => {
        console.log('Request account failed due to: ', error);
      })
      .finally(() => {
        console.log('Requesting account function was ended!');
      });

    requestSign
      .then((result: any) => {
        // publicAddress = result[0];
        console.log('Request sign was successful with this result: ', result);
      })
      .catch((error: any) => {
        console.log('Request account failed due to: ', error);
      })
      .finally(() => {
        console.log('Requesting account function was ended!');
      });

    console.log('______________________________________');
    console.log(requestAccount);
    console.log('______________________________________');
  }

  private processError(response: HttpErrorResponse): void {
    if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
      this.errorUserExists = true;
    } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
      this.errorEmailExists = true;
    } else {
      this.error = true;
    }
  }
}
