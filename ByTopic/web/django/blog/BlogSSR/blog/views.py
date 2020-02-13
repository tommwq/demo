from django.shortcuts import render, redirect
from .session import Session

import logging


def login(request):
    logging.getLogger().error('login')
    
    # 如果用户已经登录，跳转到博客列表页面。
    session = Session(request)
    if session.is_logged_in():
        return redirect('/blog')
    
    return render(request, 'blog/login.html', {})

