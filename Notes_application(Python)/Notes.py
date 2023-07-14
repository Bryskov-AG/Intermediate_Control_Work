import json
import datetime

# Определение класса Note
class Note:
    def __init__(self, title, body):
        self.id = None
        self.title = title
        self.body = body
        self.created_at = None
        self.updated_at = None

    def set_id(self, note_id):
        self.id = note_id

    def set_created_at(self, created_at):
        self.created_at = created_at

    def set_updated_at(self, updated_at):
        self.updated_at = updated_at

    def display(self):
        print(f"ID: {self.id}")
        print(f"Заголовок: {self.title}")
        print(f"Тело: {self.body}")
        print(f"Дата создания: {self.created_at}")
        print(f"Дата последнего изменения: {self.updated_at}")
        print()

# Определение класса NoteManager        

class NoteManager:
    def __init__(self):
        self.notes = []

    def load_notes(self):
        try:
            with open("notes.json", "r") as file:
                data = json.load(file)
                for note_data in data:
                    note = Note(note_data["title"], note_data["body"])
                    note.set_id(note_data["id"])
                    note.set_created_at(note_data["created_at"])
                    note.set_updated_at(note_data["updated_at"])
                    self.notes.append(note)
        except FileNotFoundError:
            # Если файл не найден, создаем пустой список заметок
            self.notes = []

    def save_notes(self):
        data = []
        for note in self.notes:
            note_data = {
                "id": note.id,
                "title": note.title,
                "body": note.body,
                "created_at": note.created_at,
                "updated_at": note.updated_at
            }
            data.append(note_data)

        with open("notes.json", "w") as file:
            json.dump(data, file, indent=4)

    def generate_note_id(self):
        if len(self.notes) == 0:
            return "0001"
        else:
            latest_id = int(self.notes[-1].id)
            new_id = str(latest_id + 1).zfill(4)
            return new_id

    def create_note(self, title, body):
        note_id = self.generate_note_id()
        current_time = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

        note = Note(title, body)
        note.set_id(note_id)
        note.set_created_at(current_time)
        note.set_updated_at(current_time)

        self.notes.append(note)
        self.save_notes()
        print("Заметка успешно создана!")

    def display_notes(self):
        for note in self.notes:
            note.display()

    def find_note_by_date(self, date):
        found_notes = []
        for note in self.notes:
            if note.created_at.startswith(date):
                found_notes.append(note)

        if len(found_notes) == 0:
            print("Заметки по указанной дате не найдены.")
        else:
            for note in found_notes:
                note.display()

    def edit_note(self, note_id, title, body):
        for note in self.notes:
            if note.id == note_id:
                note.title = title
                note.body = body
                note.updated_at = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
                self.save_notes()
                print("Заметка успешно отредактирована.")
                return

        print("Заметка с указанным ID не найдена.")

    def delete_note(self, note_id):
        for note in self.notes:
            if note.id == note_id:
                self.notes.remove(note)
                self.save_notes()
                print("Заметка успешно удалена.")
                return

        print("Заметка с указанным ID не найдена.")
        
# Основная функция main()

def main():
    note_manager = NoteManager()
    note_manager.load_notes()

    while True:
        print()
        print("===== Команды =====")
        print("1. Вывести все заметки")
        print("2. Создать новую заметку")
        print("3. Редактировать заметку")
        print("4. Удалить заметку")
        print("5. Найти заметки по дате")
        print("0. Выйти из приложения")
        print("===================")

        command = input("Введите номер команды: ")

        if command == "1":
            note_manager.display_notes()
        elif command == "2":
            title = input("Введите заголовок заметки: ")
            body = input("Введите текст заметки: ")
            note_manager.create_note(title, body)
        elif command == "3":
            note_id = input("Введите ID заметки для редактирования: ")
            title = input("Введите новый заголовок заметки: ")
            body = input("Введите новый текст заметки: ")
            note_manager.edit_note(note_id, title, body)
        elif command == "4":
            note_id = input("Введите ID заметки для удаления: ")
            note_manager.delete_note(note_id)
        elif command == "5":
            date = input("Введите дату для поиска заметок (в формате ГГГГ-ММ-ДД): ")
            note_manager.find_note_by_date(date)
        elif command == "0":
            print("До свидания!")
            break
        else:
            print("Некорректная команда. Попробуйте ещё раз.")

if __name__ == "__main__":
    main()