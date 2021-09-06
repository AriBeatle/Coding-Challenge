from django.db import models

class Trainer(models.Model):
    id = models.AutoField(primary_key=True)
    first_name = models.CharField(max_length=50)
    last_name = models.CharField(max_length=50)
    email = models.EmailField()
    phone = models.CharField(max_length=20)
    zip_code = models.CharField(max_length=6)
    certified = models.BooleanField(default=False)
    background = models.TextField(max_length=150, blank=True)
