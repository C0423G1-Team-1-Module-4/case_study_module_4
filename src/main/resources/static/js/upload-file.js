import firebase from "./configuration-firebase.js"

const uploadedURLs = [];

async function handleUpload(e) {
    console.dir(e);
    const ref = firebase.storage().ref();
    const files = e.target.files;

    for (const file of files) {
        const name = +new Date() + "-" + file.name;
        const metadata = {
            contentType: file.type
        };

        try {
            const snapshot = await ref.child(name).put(file, metadata);
            const url = await snapshot.ref.getDownloadURL();

            console.log(url);
            uploadedURLs.push(url);

            if (uploadedURLs.length === files.length) {
                alert('Tất cả ảnh đã được tải lên thành công');
                document.getElementById("image").value = uploadedURLs[0];
                document.getElementById("imageOne").value = uploadedURLs[1];
                document.getElementById("imageTwo").value = uploadedURLs[2];
                document.getElementById("imageThree").value = uploadedURLs[3];
            }
        } catch (error) {
            console.error(error);
        }
    }
}


document.getElementById("upload-file").addEventListener("change", function (e) {
    handleUpload(e)
});

function clickAdd() {
    uploadedURLs.forEach(function (url, i) {
        $.ajax({
            url: `http://localhost:8080/api/vehicle/vehicle/${url}`,
            type: "POST",
            data: JSON.stringify(uploadedURLs[i]),
            contentType: "application/json",
            success: function (response) {
                console.log("API response:", response);
            },
            error: function (error) {
                console.error("There was a problem with the AJAX request:", error);
            },
        });
    });
}




