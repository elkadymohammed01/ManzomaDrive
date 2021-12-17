from django.conf.urls import url
from django.contrib import admin
from django.contrib.staticfiles.urls import staticfiles_urlpatterns
import manzoma_drive.views
from SuperDrive import settings
from django.conf.urls.static import static
urlpatterns = [

    url(r'^admin/', admin.site.urls),
    url(r'^share/', manzoma_drive.views.share_file),
    url(r'^sharefile/', manzoma_drive.views.share_dir),
    url(r'^delete/', manzoma_drive.views.delete_file),
    url(r'^move/', manzoma_drive.views.move_file),
    url(r'^file/(?P<path>\w{0,50})', manzoma_drive.views.download_file),
    url(r'^dir/', manzoma_drive.views.make_dir),
    url('', manzoma_drive.views.show_view),
              ]

