from django.core.files.storage import FileSystemStorage
import os
from django.core.files import File
from django.http import FileResponse


class ManzomaSystemStorage(FileSystemStorage):
    soft_delete_path = "bin"
    GenralLocation = "manzoma_file_drive"
    OldFileLocation = ""
    def make_dir(self,path):
        if not self.exists(path):
            os.makedirs(os.path.join(self.location, path))
        else:
            print("Directory is Already Find")

    def copy(self, old_path, new_path, file_name):
        try:
            file_path = open(self.location + "/" + old_path + "/" + file_name)
            file = File(file_path)
            self.save(name=new_path+"/"+file_name, content=file)

        except:

            print("Problem")

    def move(self, old_path, new_path, file_name):
        self.copy(old_path, new_path, file_name)
        self.delete(old_path+"/"+file_name)

    def soft_delete(self, path, file_name):
        self.move(path, self.soft_delete_path, file_name)


manzoma_system_storage = ManzomaSystemStorage(location="manzoma_file_drive/",base_url="manzoma_file_drive/" )