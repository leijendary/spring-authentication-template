package com.leijendary.spring.authenticationtemplate.controller.v1;

import com.leijendary.spring.authenticationtemplate.controller.AbstractController;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRefreshRequestV1;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRequestV1;
import com.leijendary.spring.authenticationtemplate.data.request.v1.TokenRevokeRequestV1;
import com.leijendary.spring.authenticationtemplate.data.response.DataResponse;
import com.leijendary.spring.authenticationtemplate.data.response.v1.TokenResponseV1;
import com.leijendary.spring.authenticationtemplate.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

import static com.leijendary.spring.authenticationtemplate.controller.AbstractController.BASE_API_PATH;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping(BASE_API_PATH + "/v1/token")
@RequiredArgsConstructor
@Api("Endpoints for token related actions like getting a token, refreshing a token, or revoking a token")
public class TokenControllerV1 extends AbstractController {

    private final TokenService tokenService;

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Creates an access token with refresh token. The tokens will be saved to the database for " +
            "reference until revoked")
    public CompletableFuture<DataResponse<TokenResponseV1>> create(@RequestBody final TokenRequestV1 request) {
        final var tokenResponse = tokenService.create(request);
        final var response = DataResponse.<TokenResponseV1>builder()
                .data(tokenResponse)
                .status(CREATED)
                .object(TokenResponseV1.class.getSimpleName())
                .build();

        return completedFuture(response);
    }

    @PostMapping("refresh")
    @ApiOperation("Refreshes an access token using the refresh token provided. The refresh token must exist and " +
            "the expiration date should be after the current date")
    public CompletableFuture<DataResponse<TokenResponseV1>> refresh(@RequestBody final TokenRefreshRequestV1 request) {
        final var tokenResponse = tokenService.refresh(request);
        final var response = DataResponse.<TokenResponseV1>builder()
                .data(tokenResponse)
                .object(TokenResponseV1.class.getSimpleName())
                .build();

        return completedFuture(response);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Revokes the access token")
    public CompletableFuture<Void> revoke(@RequestBody final TokenRevokeRequestV1 request) {
        tokenService.revoke(request);

        return completedFuture(null);
    }
}
