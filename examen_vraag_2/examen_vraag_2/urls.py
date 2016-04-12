"""examen_vraag_2 URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.9/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url
from django.conf.urls import include
from django.shortcuts import redirect
#from django.contrib import admin

# Omdat de root van ons project normaal gezien een error zou opleveren redirecten we naar de url van de app
def redirect_to_chuck_norris(request):
    return redirect('/chuck_norris')

urlpatterns = [
    # Voor deze opgave was er geen admin nodig
    #url(r'^admin/', admin.site.urls),

    url(r'^$', redirect_to_chuck_norris),
    url(r'^chuck_norris/', include('chuck_norris.urls'))
]
