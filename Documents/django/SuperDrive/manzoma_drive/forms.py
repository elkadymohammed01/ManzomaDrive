from django import forms
from django.contrib.auth.models import User

class UploadFileForm(forms.Form):

    file_copy = forms.FileField()


class CreateDirForm(forms.Form):

    dir_name = forms.CharField()

class ShareFile(forms.Form):

    list_user=[]
    x=0

    for user in User.objects.all():
        x+=1
        list_user.append((str(x),user.username))

    user_name = forms.ChoiceField(choices = list_user)

