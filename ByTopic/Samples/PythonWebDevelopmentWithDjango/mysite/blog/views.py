from django.shortcuts import render
from django.http import HttpResponse
from django.template import loader
from .models import BlogPost

def archive(request):
    posts = BlogPost.objects.all()
    tpl = loader.get_template("archive.html")
    ctx = {'posts': posts}
    return HttpResponse(tpl.render(ctx, request))


