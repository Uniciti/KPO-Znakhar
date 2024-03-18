import requests

# Замените URL на адрес вашего сервера
url = 'http://localhost:5000/upload-image'

# Путь к изображению, которое вы хотите отправить
image_path = 'G:/data/картинки/123.png'

# Отправляем POST запрос на сервер с изображением в теле запроса
files = {'file': open(image_path, 'rb')}
response = requests.post(url, files=files)

# Печатаем результат запроса
print(response.json())
