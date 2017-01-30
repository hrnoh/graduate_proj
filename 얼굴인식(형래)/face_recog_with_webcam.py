import cv2
import sys

cascPath = './cascade/haarcascade_frontalface_default.xml'
faceCascade = cv2.CascadeClassifier(cascPath)

video_capture = cv2.VideoCapture(0)

while True:
    # Capture frame-by-frame
    # ret : return code로 영상의 끝을 확인 (웹캠이나 라즈베리 카메라에서는 필요 X)
    # frame : 영상의 한 프레임
    ret, frame = video_capture.read()

    # frame 흑백화
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    # 얼굴 인식
    faces = faceCascade.detectMultiScale(gray, 1.1, 5, 0, (30, 30))

    '''
    image 실제 이미지
    objects [반환값] 얼굴 검출 위치와 영역 변수
    scaleFactor 이미지 스케일
    minNeighbors 얼굴 검출 후보들의 갯수
    flags 이전 cascade와 동일하다 cvHaarDetectObjects 함수 에서
          새로운 cascade에서는 사용하지 않는다.
    minSize 가능한 최소 객체 사이즈
    maxSize 가능한 최대 객체 사이즈
    '''

    # Draw a rectangle around the faces
    for (x, y, w, h) in faces:
        cv2.rectangle(frame, (x, y), (x+w, y+h), (0, 255, 0), 2)

    # Display the resulting frame
    cv2.imshow('Video', frame)

    key = cv2.waitKey(1)

    '''
    'q' 를 누르면 종료, 's'를 누르면 얼굴 캡쳐
    '''
    if key == ord('q'):
        break
    elif key == ord('s'):
        for (x, y, w, h) in faces:
            subFace = frame[y+5:y+h-5, x+5:x+w-5]
            face_file_name = "faces/face_" + str(y) + ".jpg"
            cv2.imwrite(face_file_name, subFace)

# When everything is done, release the capture
video_capture.release()
cv2.destroyAllWindows()