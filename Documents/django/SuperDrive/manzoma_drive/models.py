from django.db import models
from django.contrib.auth.models import User

class SharedFile(models.Model):

    file_path = models.CharField(max_length=500)
    file_name = models.CharField(max_length=500)
    file_viewes =models.ManyToManyField(User)

