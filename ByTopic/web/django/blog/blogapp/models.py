from django.contrib import admin
from django.db import models

class User(models.Model):

    username = models.CharField(max_length=20)
    password = models.CharField(max_length=20)
    
class Blog(models.Model):

    user = models.ForeignKey(User, on_delete=models.CASCADE)
    title = models.CharField(max_length=80)
    content = models.TextField()
    publish_date = models.DateTimeField()


admin.site.register(User)
admin.site.register(Blog)

