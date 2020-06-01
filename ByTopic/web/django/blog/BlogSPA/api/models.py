from django.db import models
from django.contrib import admin

class User(models.Model):
    username = models.CharField(max_length=32)
    password = models.CharField(max_length=32)

@admin.register(User)
class UserAdmin(admin.ModelAdmin):
    list_display = ('id', 'username')

class Blog(models.Model):
    author = models.ForeignKey(User, on_delete=models.CASCADE)
    title = models.CharField(max_length=32)
    content = models.TextField()
    publish_date = models.DateTimeField();

@admin.register(Blog)
class BlogAdmin(admin.ModelAdmin):
    list_display = ('id', 'title', 'author__name', 'publish_date')
    def author__name(self, obj):
        return obj.author.username

    
    
