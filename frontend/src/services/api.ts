const BASE_URL = import.meta.env.VITE_API_URL ?? "http://localhost:8080";

export interface PasswordRequest {
    length: number;
    uppercase: boolean;
    lowercase: boolean;
    numbers: boolean;
    symbols: boolean;
    excludeAmbiguous: boolean;
    quantity: number;
}

export interface PasswordResponse {
    passwords: string[];
    length: number;
    generateAt: string;
}

export async function generatePasswords(
    request: PasswordRequest
): Promise<PasswordResponse> {
    const response = await fetch(`${BASE_URL}/api/v1/passwords/generate`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(request),
        signal: AbortSignal.timeout(40_000),
    });

    if(!response.ok) {
        const error = await response.json();
        throw new Error(error.message ?? "Erro ao gerar senhas");
    }

    return response.json();
}