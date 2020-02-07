from django.shortcuts import render, get_object_or_404, redirect
from .models import Blog, User
import datetime


def blog_list(request):
    if 'username' in request.session:
        user_login = True
        username = request.session['username']
    else:
        user_login = False
        username = ""

    return render(request, 'blog_list.html', {
        'user_login': user_login,
        'username': username,
        'blog_list': Blog.objects.all()
    })

def blog(request, blog_id):
    return render(request, 'blog.html', {'blog_content': get_object_or_404(Blog, pk=blog_id).content})

def blog_edit(request):
    if 'username' in request.session:
        user_login = True
        username = request.session['username']
    else:
        user_login = False
        username = ""

    return render(request, 'blog_edit.html', {
        'user_login': user_login,
        'username': username
    })

def user_login(request):
    return render(request, 'user_login.html', {})

def user_login_process(request):
    username = request.POST['username']
    password = request.POST['password']
    
    matched_users = User.objects.filter(username=username, password=password)
    if len(matched_users) == 0:
        return redirect('/user/login')
    else:
        request.session['user_id'] = matched_users[0].id
        request.session['username'] = matched_users[0].username
        return redirect('/blog')
        
def blog_submit(request):
    if not 'username' in request.session:
        return redirect('/blog')
        
    user_login = True
    username = request.session['username']
    user_id = request.session['user_id']
    title = request.POST['title']
    content = request.POST['content']

    blog = Blog(user=User.objects.get(id=user_id),
                title=title,
                content=content,
                publish_date=datetime.datetime.now())
    blog.save()
    
    return redirect('/blog')
