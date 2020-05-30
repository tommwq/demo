from django.forms.models import model_to_dict
from django.http.response import JsonResponse
from django.shortcuts import render

from .models import *

def update(aDict, key, value):
    aDict[key] = value
    return aDict

def ok(data={}): return JsonResponse({'code': 0, 'error': '', 'payload': data})
def err(e): return JsonResponse({'code': 1, 'error': str(e), 'payload': {}})

def login(request):
    try:
        username = request.POST['username']
        password = request.POST['password']
        user = User.objects.get(username=username, password=password)
        return ok(model_to_dict(user, exclude=['password']))
    except Exception as e:
        return err(e)

def register(request):
    try:
        username = request.POST['username']
        password = request.POST['password']
        matched_users = User.objects.filter(username=username)
        if len(matched_users) > 0:
            raise Exception('duplicated username')

        user = User(username=username, password=password)
        user.save()
        return ok(model_to_dict(user, exclude=['password']))
    except Exception as e:
        return err(e)

def blog(request):
    blogList = Blog.objects.all()
    return ok([update(model_to_dict(x), 'author', x.author.username) for x in blogList])

def blog_detail(request, blog_id):
    try:
        blog = Blog.objects.get(id=blog_id)
    except Exception as e:
        return err(e)
    
    return ok(update(model_to_dict(blog), 'author', blog.author.username))


    
        
