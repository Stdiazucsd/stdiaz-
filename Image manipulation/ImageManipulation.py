from PIL import Image

def put_img1_and_img2_in_center(img1_path, img2_path, img3_path):
    img1 = Image.open(img1_path)
    img2 = Image.open(img2_path)
    img3 = Image.open(img3_path)
    
    width1, height1 = img1.size
    width2, height2 = img2.size
    width3, height3 = img3.size

    img3 = img3.rotate(270)
    # find total width required for img1 and img2
    total_width = width1 + width2
    # find the positions to place img1 and img2 side by side at the bottom of img3
    y = height3 - max(height1, height2)
    x1 = (width3 - total_width) // 2
    x2 = x1 + width1
    # make a new blank image with the size of img3
    combined_img = Image.new('RGB', img3.size)
    
    # Copy img3 to the combined image
    for y_coord in range(height3):
        for x_coord in range(width3):
            combined_img.putpixel((x_coord, y_coord), img3.getpixel((x_coord, y_coord)))
    
    # Paste img1 and img2 side by side at the bottom of the combined image
    for y_coord in range(y, height3):
        for x_coord in range(x1, min(x2, width3)):
            if x_coord - x1 < width1 and y_coord - y < height1:
                combined_img.putpixel((x_coord, y_coord), img1.getpixel((x_coord - x1, y_coord - y)))
        for x_coord in range(x2, min(x2 + width2, width3)):
            if x_coord - x2 < width2 and y_coord - y < height2:
                combined_img.putpixel((x_coord, y_coord), img2.getpixel((x_coord - x2, y_coord - y)))
    
    combined_img.show()
    return combined_img
put_img1_and_img2_in_center("C:\\Users\\Steve\\Downloads\\friends.jpg", "C:\\Users\\Steve\\Downloads\\ens.jpg", "C:\\Users\\Steve\\Downloads\\back.jpg")
 
def make_red_snowcone_to_green(image_path):
    img = Image.open(image_path)
    width, height = img.size
    # look at each pixel in the image and change it to green if it is bright red
    for i in range(width):
        for j in range(height):
            r, g, b = img.getpixel((i, j))
            if r > 212 and g < 100 and b < 125:  # Check for bright red pixels
                img.putpixel((i, j), (0, 250, 0))
        
    img.rotate(270).show() 
    return img  
make_red_snowcone_to_green("C:\\Users\\Steve\\Downloads\\d.jpg")

def reflect_and_combine(image_path):
    img = Image.open(image_path)
    width, height = img.size
    
    # Create a new blank image with double the width of img
    combined_img = Image.new('RGB', (width * 2, height)) 
    # Copy the original image to the left side of the combined image
    for y in range(height):
        for x in range(width):
            pixel = img.getpixel((x, y))
            combined_img.putpixel((x, y), pixel)   
    # Reflect the original image and paste it to the right side of the combined image
    for y in range(height):
        for x in range(width):
            pixel = img.getpixel((width - x - 1, y))
            combined_img.putpixel((width + x, y), pixel)
    
    combined_img.show()
    return combined_img
reflect_and_combine("C:\\Users\\Steve\\Downloads\\s.jpg")

def lusito_half_alien(image_path):
    img = Image.open(image_path)
    width, height = img.size
    # find the width of the left half of the image
    left_half_width = width // 2
    
    # Invert the colors of the left half of the image and just a little on the right half 
    for i in range(left_half_width- 1415, left_half_width + 100):
        for j in range(height):
            r, g, b = img.getpixel((i, j))
            img.putpixel((i, j), (250 - r, 250 - g, 250 - b))
    
    img.show()
    return img
lusito_half_alien("C:\\Users\\Steve\\Downloads\\luis.jpg")










