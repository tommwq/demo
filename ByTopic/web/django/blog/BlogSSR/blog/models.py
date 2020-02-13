from django.db import models

class User(models.Model):
    username = models.CharField(max_length=32)
    password = models.CharField(max_length=32)

class Publishable(models.Model):
    class Meta:
        abstract = True
        ordering = ('-publish_date', )
        
    author = models.ForeignKey(User, on_delete=models.CASCADE)
    content = models.TextField()
    publish_date = models.DateTimeField()

class Blog(Publishable):
    title = models.CharField(max_length=60)

class Comment(Publishable):
    blog = models.ForeignKey(Blog, on_delete=models.CASCADE)

