import zipfile, xml.etree.ElementTree as ET
with zipfile.ZipFile('c:/Projetos/antigravity/raac_system/prompt_backend_springboot.docx') as docx:
    tree = ET.fromstring(docx.read('word/document.xml'))
    text = '\n'.join([node.text for node in tree.iter() if node.tag.endswith('}t') and node.text])
    with open('c:/Projetos/antigravity/raac_system/prompt_extracted.txt', 'w', encoding='utf-8') as f:
        f.write(text)
