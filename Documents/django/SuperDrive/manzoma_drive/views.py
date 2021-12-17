from django.contrib.auth.models import User
from django.shortcuts import render
from .forms import UploadFileForm , CreateDirForm , ShareFile
from .ManzomaSystemStorage import manzoma_system_storage
from django.views.decorators.csrf import requires_csrf_token
from django.http import HttpResponse, HttpResponseNotFound
from .Temp import FileInformation
from .models import SharedFile


@requires_csrf_token
def show_view(request):

    path = ""
    if str(request.user.pk) == 'None':
        return render(request,'new_post.html',{})

    elif (not manzoma_system_storage.exists(str(request.user.pk))) \
            and manzoma_system_storage.location == manzoma_system_storage.GenralLocation:
        manzoma_system_storage.make_dir(path=str(request.user.pk))
        manzoma_system_storage.location = manzoma_system_storage.GenralLocation+str(request.user.pk)
    path = str(getURL(request.path))
    if path == "/":
        request.path="/"+str(request.user.pk)
        path = "/"+str(request.user.pk)
    else :
        path = str(getURL(request.path))
    manzoma_system_storage.location =manzoma_system_storage.GenralLocation+path


    form = UploadFileForm()

    files = []
    dirs = []
    raw_item = 0

    try:
        list_file = manzoma_system_storage.listdir("")[1]
        list_dir = manzoma_system_storage.listdir("")[0]
        for file in list_file:

            item = FileInformation()
            item.index = raw_item % 4
            raw_item += 1
            item.title = file
            item.nike_name = (file.split('.')[0])[0:min(7, len(file))]
            item.size = str(manzoma_system_storage.size(file) / (1024.0*1024 ))[0:4] + "Mb"
            item.adding_time = manzoma_system_storage.created_time(file)
            while len(item.nike_name) <= 8:
                item.nike_name = item.nike_name + ' '
            files.append(item)

        for dir in list_dir:

            item = FileInformation()
            item.title = dir
            item.nike_name = dir[0:min(7, len(dir))].split('.')[0]
            while len(item.nike_name) <= 8:
                item.nike_name = item.nike_name + ' '
            item.size = str(manzoma_system_storage.size(dir) / (1024.0 ** 2)) + "Mb"
            item.adding_time = manzoma_system_storage.created_time(dir)
            dirs.append(item)
    except:
        pass


    if request.method == 'POST':

        file = request.FILES['file_copy']

        manzoma_system_storage.save(file.name, file)
        file_item = SharedFile(file_name=file.name, file_path=path+"/"+file.name)
        file_item.save()

        file_item.file_viewes.add(request.user)


    return render(request, 'new_post.html',
                  {'form': form, 'list_file': files, 'list_dir': dirs ,'path' : request.path})


def download_file(request, path):
    fs = manzoma_system_storage
    filename = str(request.path).split('/')[-1]
    file_extantion=filename.split('.')[1]
    if fs.exists(filename):
        with fs.open(filename) as pdf:
            response = HttpResponse(pdf, content_type='application/'+file_extantion)
            response[
                'Content-Disposition'] = 'inline; filename= "'+filename+'"'
            return response
    else:
        return HttpResponseNotFound('The requested pdf was not found in our server.')


def make_dir(request):

    form = CreateDirForm

    if str(request.user.pk) == 'None':

        return render(request, 'new_post.html', {})

    elif (not manzoma_system_storage.exists(str(request.user.pk))) \
            and manzoma_system_storage.location == manzoma_system_storage.GenralLocation:
        manzoma_system_storage.make_dir(path=str(request.user.pk))
        manzoma_system_storage.location = manzoma_system_storage.GenralLocation+str(request.user.pk)
    path = str(getURL(request.path))
    if path == "/dir/":
        path = path+str(request.user.pk)
    else :
        path=str(getURL(request.path))

    manzoma_system_storage.location =manzoma_system_storage.GenralLocation+path[4:]

    files = []
    dirs = []
    raw_item = 0

    if request.method == 'POST':
        manzoma_system_storage.make_dir(request.POST['dir_name'])

    try:
        list_file = manzoma_system_storage.listdir("")[1]
        list_dir = manzoma_system_storage.listdir("")[0]
        for file in list_file:

            item = FileInformation()
            item.index = raw_item % 4
            raw_item += 1
            item.title = file
            item.nike_name = (file.split('.')[0])[0:min(7, len(file))]
            item.size = str(manzoma_system_storage.size(file) / (1024.0 ** 2)) + "Mb"
            item.adding_time = manzoma_system_storage.created_time(file)
            while len(item.nike_name) <= 8:
                item.nike_name = item.nike_name + ' '
            files.append(item)

        for dir in list_dir:

            item = FileInformation()
            item.title = dir
            item.nike_name = dir[0:min(7, len(dir))].split('.')[0]
            while len(item.nike_name) <= 8:
                item.nike_name = item.nike_name + ' '
            item.size = str(manzoma_system_storage.size(dir) / (1024.0 ** 2)) + "Mb"
            item.adding_time = manzoma_system_storage.created_time(dir)
            dirs.append(item)
    except:
        pass
    return render(request, 'new_post.html',
                  {'form': form, 'list_file': files, 'list_dir': dirs, 'path': request.path})

def share_dir(request):

    form =ShareFile

    if str(request.user.pk) == 'None':

        return render(request, 'new_post.html', {})

    elif (not manzoma_system_storage.exists(str(request.user.pk))) \
            and manzoma_system_storage.location == manzoma_system_storage.GenralLocation:
        manzoma_system_storage.make_dir(path=str(request.user.pk))
        manzoma_system_storage.location = manzoma_system_storage.GenralLocation+str(request.user.pk)
    path = str(getURL(request.path))
    if path == "/share/":
        path = path+str(request.user.pk)
    else :
        path=str(getURL(request.path))

    manzoma_system_storage.location =manzoma_system_storage.GenralLocation+path[6:]
    files = []
    dirs = []
    raw_item = 0

    if request.method == 'POST':
        pass

    try:
        list_file = manzoma_system_storage.listdir("")[1]
        list_dir = manzoma_system_storage.listdir("")[0]
        for file in list_file:

            item = FileInformation()
            item.index = raw_item % 4
            raw_item += 1
            item.title = file
            item.nike_name = (file.split('.')[0])[0:min(7, len(file))]
            item.size = str(manzoma_system_storage.size(file) / (1024.0 ** 2)) + "Mb"
            item.adding_time = manzoma_system_storage.created_time(file)
            while len(item.nike_name) <= 8:
                item.nike_name = item.nike_name + ' '
            files.append(item)

        for dir in list_dir:

            item = FileInformation()
            item.title = dir
            item.nike_name = dir[0:min(7, len(dir))].split('.')[0]
            while len(item.nike_name) <= 8:
                item.nike_name = item.nike_name + ' '
            item.size = str(manzoma_system_storage.size(dir) / (1024.0 ** 2)) + "Mb"
            item.adding_time = manzoma_system_storage.created_time(dir)
            dirs.append(item)

    except:
        pass
    return render(request, 'new_post.html',
                  {'form': form, 'list_file': files, 'list_dir': dirs ,'path' : request.path})

def getURL(path):

    url=""
    for char in path:
        if  char =='+':
            url+='/'
        else:
            url+=char

    return url


def delete_file(request):
    manzoma_system_storage.location=manzoma_system_storage.GenralLocation
    request.path=request.path[8:]
    manzoma_system_storage.delete(getURL(request.path))
    request.path ="/"+ request.path.split('/')[0]
    return HttpResponseNotFound('The File was deleted already Now.')

@requires_csrf_token
def share_file(request):
    form = ShareFile()

    if request.method == 'POST':
        user_pk = User.objects.get(pk=request.POST['user_name'])
        fl=getURL(request.path[6:])
        fp=SharedFile.objects.get(file_path=fl)
        fp.file_viewes.add(user_pk.pk)

    return render(request, 'share_base.html', {'form': form})

def move_file(request):

    if manzoma_system_storage.OldFileLocation == "":
        manzoma_system_storage.OldFileLocation = getURL(request.path[5:])
        request.path = "/move/"
    if str(request.user.pk) == 'None':

        return render(request, 'new_post.html', {})


    elif (not manzoma_system_storage.exists(str(request.user.pk))) \
            and manzoma_system_storage.location == manzoma_system_storage.GenralLocation:
        manzoma_system_storage.make_dir(path=str(request.user.pk))
        manzoma_system_storage.location = manzoma_system_storage.GenralLocation + str(request.user.pk)
    path = str(getURL(request.path))
    if path == "/move/":
        path = path + str(request.user.pk)
    else:
        path = str(getURL(request.path))

    manzoma_system_storage.location = manzoma_system_storage.GenralLocation + path[5:]
    files = []
    dirs = []
    raw_item = 0


    try:
        list_dir = manzoma_system_storage.listdir("")[0]
        for dir in list_dir:

            item = FileInformation()
            item.title = dir
            item.nike_name = dir[0:min(7, len(dir))].split('.')[0]
            while len(item.nike_name) <= 8:
                item.nike_name = item.nike_name + ' '
            item.size = str(manzoma_system_storage.size(dir) / (1024.0 ** 2)) + "Mb"
            item.adding_time = manzoma_system_storage.created_time(dir)
            dirs.append(item)

    except:
        pass

    if(request.method == 'POST'):

        manzoma_system_storage.location=manzoma_system_storage.GenralLocation
        file_name=manzoma_system_storage.OldFileLocation.split('/')[-1]

        old_path = manzoma_system_storage.OldFileLocation

        old_path = old_path[1:len(old_path)-len(file_name)]

        old_path =old_path[:len(old_path)-1]

        manzoma_system_storage.move(old_path=old_path,
                                    new_path=getURL(request.path[6:]),
                                    file_name=file_name)
        request.path = request.path[5:]


        request.method =''
        show_view(request)

    return render(request, 'new_post.html', {'list_file': files, 'list_dir': dirs ,'path' : path})
