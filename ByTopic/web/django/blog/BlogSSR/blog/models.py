from django.contrib import admin
from django.db import models
import logging

logger = logging.getLogger(__name__)

class User(models.Model):
    username = models.CharField(max_length=32)
    password = models.CharField(max_length=32)

@admin.register(User)
class UserAdmin(admin.ModelAdmin):
    list_display = ('id', 'username', 'password')
    
class Publishable(models.Model):
    class Meta:
        abstract = True
        ordering = ('-publish_date', )
        
    author = models.ForeignKey(User, on_delete=models.CASCADE)
    content = models.TextField()
    publish_date = models.DateTimeField()

class Blog(Publishable):
    title = models.CharField(max_length=60)

@admin.register(Blog)
class BlogAdmin(admin.ModelAdmin):
    list_display = ('id','author__name','title','publish_date')

    def author__name(self, obj):
        return obj.author.username

class Comment(Publishable):
    blog = models.ForeignKey(Blog, on_delete=models.CASCADE)

@admin.register(Comment)
class CommentAdmin(admin.ModelAdmin):
    list_display = ('id', 'author__name', 'blog__title', 'content', 'publish_date')

    def author__name(self, obj):
        return obj.author.username

    def blog__title(self, obj):
        return obj.blog.title
