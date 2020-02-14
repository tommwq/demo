from django.urls import path
from . import views

urlpatterns = [
    path('login', views.login),
    path('user/login', views.user_login),
    path('', views.blog),
    path('blog/<int:blog_id>', views.blog_detail, name='blog_detail'),
    path('blog/<int:blog_id>/comment', views.blog_comment, name='blog_comment'),
    path('user/<int:user_id>', views.user, name='user'),
    path('blog/post', views.blog_post, name='blog_post'),
    path('blog/edit', views.blog_edit, name='blog_edit')
]
