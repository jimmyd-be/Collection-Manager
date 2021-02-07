import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {NbToastrService} from '@nebular/theme';
import {Setting} from 'src/app/Entities/Setting';
import {SettingService} from '../../Services/setting.service';

@Component({
  selector: 'app-admin-settings',
  templateUrl: './admin-settings.component.html',
  styleUrls: ['./admin-settings.component.scss']
})
export class AdminSettingsComponent implements OnInit {
  settings: Setting[];
  myForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private settingService: SettingService,
              private toastrService: NbToastrService) {
  }

  ngOnInit(): void {
    this.settingService.getAllSettings().subscribe(settings => {
      this.settings = settings;

      this.myForm = this.formBuilder.group(this.formFields());
    });
  }

  public onFormSubmit() {

    const values = [];

    for (const field in this.myForm.controls) {

      if (this.myForm.controls[field]) {
        values.push(new Setting(field, this.myForm.controls[field].value));
      }
    }

    this.settingService.saveSettings(values).subscribe(
      x => this.toastrService.success('Settings are saved', 'Settings'),
      err => this.toastrService.danger('Something went wrong when saving settings', 'Settings')
    );

  }

  private formFields() {
    const empArr = {};
    for (const val of this.settings) {
      empArr[val.key] = val.value;
    }
    return empArr;
  }

}
