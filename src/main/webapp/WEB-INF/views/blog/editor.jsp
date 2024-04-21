<%--
  Created by IntelliJ IDEA.
  User: nnminh
  Date: 20/04/2024
  Time: 10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Extended Editor.js Tools Example</title>

  <!-- Editor.js CDN -->
  <script src="https://cdn.jsdelivr.net/npm/@editorjs/editorjs@latest"></script>

  <!-- Editor.js Header Plugin CDN -->
  <script src="https://cdn.jsdelivr.net/npm/@editorjs/header@latest"></script>

  <!-- Editor.js List Plugin CDN -->
  <script src="https://cdn.jsdelivr.net/npm/@editorjs/list@latest"></script>

  <!-- Editor.js Paragraph Plugin CDN -->
  <script src="https://cdn.jsdelivr.net/npm/@editorjs/paragraph@latest"></script>

  <!-- Editor.js Image Plugin CDN -->
  <script src="https://cdn.jsdelivr.net/npm/@editorjs/image@latest"></script>

  <!-- Editor.js Embed Plugin CDN -->
  <script src="https://cdn.jsdelivr.net/npm/@editorjs/embed@latest"></script>

  <!-- Editor.js Link Plugin CDN -->
  <script src="https://cdn.jsdelivr.net/npm/@editorjs/link@latest"></script>

  <!-- Editor.js Delimiter Plugin CDN -->
  <script src="https://cdn.jsdelivr.net/npm/@editorjs/delimiter@latest"></script>

  <!-- Editor.js Code Plugin CDN -->
  <script src="https://cdn.jsdelivr.net/npm/@editorjs/code@latest"></script>
</head>
<body>

<div id="editorjs"></div>

<script>
    // Initialize Editor.js
    const editor = new EditorJS({
        holder: 'editorjs',
        tools: {
            header: {
                class: Header,
                config: {
                    placeholder: 'Enter header',
                    levels: [1, 2, 3],
                    defaultLevel: 1,
                    inlineToolbar: true
                }
            },
            list: {
                class: List,
                inlineToolbar: true
            },
            paragraph: {
                class: Paragraph,
                inlineToolbar: true
            },
            image: {
                class: ImageTool,
                config: {
                    endpoints: {
                        byFile: 'https://your-backend.com/uploadFile', // Replace with your backend endpoint
                        byUrl: 'https://your-backend.com/fetchUrl' // Replace with your backend endpoint
                    }
                }
            },
            embed: {
                class: Embed,
                inlineToolbar: true
            },
            link: {
                class: LinkTool,
                config: {
                    endpoint: 'https://your-backend.com/fetchUrl' // Replace with your backend endpoint
                }
            },
            delimiter: Delimiter,
            code: {
                class: CodeTool,
                inlineToolbar: true
            }
        }
    });
    console.log('editor', editor);
</script>

</body>
</html>

