from django.shortcuts import render, redirect
from django.utils import timezone
from .models import User, Blog, Comment
from .session import Session

import logging

logger = logging.getLogger(__name__)

def login(request):
    logger.error('login')
    
    # 如果用户已经登录，跳转到博客列表页面。
    session = Session(request)
    if session.is_logged_in():
        return redirect('/')
    
    return render(request, 'login.html', {})


def user_login(request):
    session = Session(request)
    
    is_logout ='is_logout' in request.POST
    if is_logout:
        session.logout()
        return redirect('/login')

    if session.is_logged_in():
        return redirect('/')

    is_register = 'is_register' in request.POST
    username = request.POST['username']
    password = request.POST['password']

    if is_register:
        # 注册
        named_users = User.objects.filter(username=username)
        if len(named_users) > 0:
            # 已经存在同名用户
            return redirect('/login')
        else:
            user = User(username=username,password=password)
            user.save()
            session.login_as(user.id, username)
            return redirect('/')
    else:
        # 登录
        matched_users = User.objects.filter(username=username,password=password)
        if len(matched_users) == 0:
            # 密码不匹配
            return redirect('/login')
        else:
            user = matched_users[0]
            session.login_as(user.id, user.username)
            return redirect('/')

        
def blog(request):
    session = Session(request)
    if not session.is_logged_in():
        return redirect('/login')
    
    blog_list = Blog.objects.all()
    return render(request, 'blog.html', {'blog_list': blog_list})

def blog_detail(request, blog_id):
    session = Session(request)
    if not session.is_logged_in():
        return redirect('/login')
    
    blog = Blog.objects.get(id=blog_id)
    logger.error(dir(blog))
    return render(request, 'blog_detail.html', {'blog': blog})

def blog_comment(request, blog_id):
    session = Session(request)
    if not session.is_logged_in():
        return redirect('/login')

    user = User.objects.get(id=session.user_id())
    content = request.POST['content']
    comment = Comment(content=content,
                      publish_date=timezone.now(),
                      blog_id = blog_id,
                      author=user)
    comment.save()
    return redirect('/blog/' + str(blog_id))

def user(request, user_id):
    session = Session(request)
    if not session.is_logged_in():
        return redirect('/login')

    user = User.objects.get(id=session.user_id())
    return render(request, 'user.html', {'user': user})

def blog_post(request):
    session = Session(request)
    if not session.is_logged_in():
        return redirect('/login')

    user_id = session.user_id()
    title = request.POST['title']
    content = request.POST['content']
    blog = Blog(author = User.objects.get(id=user_id),
                title = title,
                content = content,
                publish_date = timezone.now())
    blog.save()
    return redirect('/user/' + str(user_id))


def blog_edit(request):
    session = Session(request)
    if not session.is_logged_in():
        return redirect('/login')
    return render(request, 'blog_edit.html', {})
