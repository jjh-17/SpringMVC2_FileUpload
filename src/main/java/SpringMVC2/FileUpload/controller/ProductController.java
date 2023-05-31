package SpringMVC2.FileUpload.controller;

import SpringMVC2.FileUpload.domain.Product;
import SpringMVC2.FileUpload.domain.UploadFile;
import SpringMVC2.FileUpload.file.FileStore;
import SpringMVC2.FileUpload.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final FileStore fileStore;

    @GetMapping("/products/new")
    public String newProduct(@ModelAttribute ProductForm productForm) {
        return "product-form";
    }

    @PostMapping("/products/new")
    public String saveProduct(@ModelAttribute ProductForm productForm,
                              RedirectAttributes redirectAttributes) throws IOException {

        UploadFile attachFile = fileStore.storeFile(productForm.getAttachFile());
        List<UploadFile> storeImageFiles = fileStore.storeFiles(productForm.getImageFiles());

        //데이터베이스에 저장
        Product product = new Product();
        product.setProductName(productForm.getProductName());
        product.setAttachFile(attachFile);
        product.setImageFiles(storeImageFiles);
        productRepository.save(product);

        redirectAttributes.addAttribute("productId", product.getId());

        return "redirect:/products/{productId}";
    }

    @GetMapping("/products/{id}")
    public String products(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id);
        model.addAttribute("product", product);

        return "product-view";
    }

    @ResponseBody
    @GetMapping("/images/{fileName}")
    public Resource downloadImage(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(fileName));
    }

    @GetMapping("attach/{productId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long productId) throws MalformedURLException {
        Product product = productRepository.findById(productId);
        String storeFileName = product.getAttachFile().getStoreFileName();
        String uploadFileName = product.getAttachFile().getUploadFileName();
        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));
        log.info("uploadFileName={}", uploadFileName);

        //한글 깨짐 방지
        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);

        //다운로드를 위한 헤더 정보
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
