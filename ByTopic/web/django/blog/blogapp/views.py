from django.shortcuts import render, get_object_or_404, redirect
from .models import Blog, User

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

def blog_edit():
    pass

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
        
    
