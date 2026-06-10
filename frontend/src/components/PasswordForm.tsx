import type { PasswordRequest } from "../services/api";

interface props {
    onGenerate: (req: PasswordRequest) => void
    loading: boolean;
}