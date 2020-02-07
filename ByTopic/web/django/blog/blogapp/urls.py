from django.urls import path
from . import views

urlpatterns = [
    path('', views.blog_list, name='blog_list'),
    path('blog/<int:blog_id>', views.blog, name='blog'),
    path('blog/edit', views.blog_edit, name='blog_edit'),
    path('user/login', views.user_login, name='user_login'),
    path('user/login/process', views.user_login_process, name='user_login_process'),
]
