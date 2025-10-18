# 🖼️ Image Upload Gallery

A simple **Java Servlet + JSP web application** that lets users **upload images**, store them on **ImgBB (via API)**, and display them in a **gallery view**.  
The app uses **MySQL** to store image metadata like name, URL, and upload timestamp.
---

🌐 **Live Demo:** [https://img-upload-gallery.onrender.com](https://img-upload-gallery.onrender.com)


---

## 🚀 Features
- 📤 Upload images via a responsive web form  
- 🌐 Hosted using **ImgBB API**  
- 🗄️ Store image URLs and details in **MySQL**  
- 🖼️ View all images in a dynamic gallery  
- ⚙️ Built using **Java Servlets, JSP, Maven, and Tomcat**

---

## ⚙️ Tech Stack

| Layer | Technology |
|-------|-------------|
| Frontend | HTML, CSS, JSP |
| Backend | Java Servlets |
| Database | MySQL |
| API | ImgBB API |
| Server | Apache Tomcat |
| Build Tool | Maven |

---

## 🧠 How It Works

1. User uploads an image through the web form.  
2. The **`UploadImageServlet`** reads the image file and encodes it.  
3. The app sends a **POST request** to the **ImgBB API** with your API key.  
4. ImgBB responds with a **public image URL**.  
5. The URL and file name are saved into a **MySQL database**.  
6. The **gallery page** fetches and displays all uploaded images.

---

## 🧩 API Reference

#### Upload Image

```http
POST /UploadImageServlet
```

| Parameter | Type | Description |
| :-------- | :--- | :----------- |
| `file` | `multipart/form-data` | **Required.** The image file uploaded by the user |
| `IMGBB_API_KEY` | `string` | **Required.** Your ImgBB API key for authentication |

**Response Example:**
```json
{
  "success": true,
  "url": "https://i.ibb.co/example/image.png",
  "message": "Image uploaded successfully"
}
```

---

#### Get All Images

```http
GET /GalleryServlet
```

| Parameter | Type | Description |
| :-------- | :--- | :----------- |
| `none` | — | Returns all images stored in the database |

**Response Example:**
```json
[
  {
    "id": 1,
    "name": "sample.jpg",
    "url": "https://i.ibb.co/example/sample.jpg",
    "uploaded_at": "2025-10-17 14:22:00"
  }
]
```

---

#### ImgBB API (External)

```http
POST https://api.imgbb.com/1/upload
```

| Parameter | Type | Description |
| :-------- | :--- | :----------- |
| `key` | `string` | **Required.** Your ImgBB API key |
| `image` | `base64` | **Required.** The image encoded in Base64 format |

**Response Example:**
```json
{
  "data": {
    "url": "https://i.ibb.co/example/image.png"
  },
  "success": true,
  "status": 200
}
```

---

## 🗃️ Database Schema

**Database Name:** `img_gallery`  
**Table Name:** `images`

| Column | Type | Description |
| :------ | :--- | :----------- |
| `id` | INT (AUTO_INCREMENT) | Primary key |
| `name` | VARCHAR(255) | Original image name |
| `url` | VARCHAR(500) | ImgBB hosted image URL |
| `uploaded_at` | TIMESTAMP | Upload date/time |

**SQL Example:**
```sql
CREATE TABLE images (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  url VARCHAR(500) NOT NULL,
  uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## 📁 File Structure

```
img-upload-gallery/
│
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/
│       │       ├── UploadImageServlet.java
│       │       ├── GalleryServlet.java
│       │       └── DbConnection.java
│       │
│       ├── webapp/
│       │   ├── index.jsp
│       │   ├── upload.jsp
│       │   ├── gallery.jsp
│       │   └── WEB-INF/
│       │       └── web.xml
│       │
│       └── resources/
│           └── db.properties
│
└── README.md
```

---

## 🛠️ Setup & Run

### 1️⃣ Prerequisites
- Java 17+  
- Maven 3.9+  
- Apache Tomcat 10+  
- MySQL Server  
- ImgBB API Key

### 2️⃣ Configure Database
Update credentials in `DbConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/img_gallery";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
```

### 3️⃣ Add ImgBB API Key
Inside `UploadImageServlet.java`:
```java
final String IMGBB_API_KEY = "your_imgbb_api_key_here";
```

### 4️⃣ Run the App
```bash
mvn clean package
```
Deploy the generated `.war` to **Tomcat**, then visit:  
👉 `http://localhost:8080/img-upload-gallery/`

---

## 💡 Future Improvements
- Add user login/logout system  
- Add image deletion & search features  
- Use AJAX for smoother uploads  
- Support multiple image upload  

---

## 🧑‍💻 Author
**Rishi Sahu (unknownhero88)**  
📍 Java | JSP | MySQL | API Integration  
🔗 [GitHub Profile](https://github.com/unknownhero88)
